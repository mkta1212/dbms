import Button from '@mui/material/Button'
import Collapse from '@mui/material/Collapse'
import IconButton from '@mui/material/IconButton'
import Paper from '@mui/material/Paper'
import Table from '@mui/material/Table'
import TableBody from '@mui/material/TableBody'
import TableCell from '@mui/material/TableCell'
import TableContainer from '@mui/material/TableContainer'
import TableHead from '@mui/material/TableHead'
import TableRow from '@mui/material/TableRow'
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography'
import TextField from '@mui/material/TextField'
import Select from '@mui/material/Select';
import MenuItem from '@mui/material/MenuItem';
import InputLabel from '@mui/material/InputLabel';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { DemoContainer, DemoItem } from '@mui/x-date-pickers/internals/demo';
import axios from 'axios';
import authHeader from '../services/auth-header';
import { useEffect, useState } from 'react'
import UserService from "../services/user.service";
import KeyboardArrowDownIcon from '@mui/icons-material/KeyboardArrowDown'
import KeyboardArrowUpIcon from '@mui/icons-material/KeyboardArrowUp'
import ToggleButton from '@mui/material/ToggleButton';
import ToggleButtonGroup from '@mui/material/ToggleButtonGroup';
import CircularProgress from '@mui/material/CircularProgress';
import { DesktopTimePicker } from '@mui/x-date-pickers/DesktopTimePicker';
import { DigitalClock } from '@mui/x-date-pickers/DigitalClock';
import { format } from 'date-fns';
import { Time } from 'time-js'
import dayjs from 'dayjs'
import moment from 'moment/moment'
function ClassroomBlock(props){
    const {classroom, periodList} = props
    return(
        <>
            <TableRow >
                <TableCell  width="100%" >{classroom.roomName}</TableCell>
            </TableRow>
            <TableCell sx={{ borderBottom: "none"}} >
                {periodList.map((period,index)=>{
                    if(period===0 && index>=8 && index<=21){
                      return(<Button variant='outlined' style={{ margin: 2 }} id={classroom.roomName+index}>{index}:00</Button>)
                    }
                    else if(period===1){
                      return (<Button variant='outlined' style={{ margin: 2 }} disabled>{index}:00</Button>)
                    }
                    return ""
                   
               
            })}
            </TableCell>
        </>
    )
}
function Row(props){
  // 設定使用者下拉式選單開闔
  const [open, setOpen] = useState(false)
  const {buildingName, classrooms, desiredTime} = props
    // console.log(buildingName)
    // console.log(classrooms)
  return (
    <>
      <TableRow  sx={{ borderBottom: 1 }}>
        <TableCell>
          <IconButton
            aria-label='expand row'
            size='small'
            onClick={() => setOpen(!open)}
          >
            {open ? <KeyboardArrowUpIcon /> : <KeyboardArrowDownIcon />}
          </IconButton>
        </TableCell>
        <TableCell align='center' width="100%">{buildingName}</TableCell>
      </TableRow>
      <TableRow>
        <TableCell width="100%" style={{ paddingBottom: 1, paddingTop: 0, margin: 2 }} colSpan={6}>
          <Collapse in={open} timeout='auto' unmountOnExit>
            <Box sx={{ margin: 1 }}>
              
              {classrooms.map((pair)=>{
                if(desiredTime!==0){
                  return <ClassroomBlock classroom = {pair[0]} periodList = {pair[1]}/>
                }
                else{
                  console.log(desiredTime)
                  if(pair[1][desiredTime]===0){
                    return <ClassroomBlock classroom = {pair[0]} periodList = {pair[1]}/>
                  }
                }
                return ""
              })}
            </Box>
          </Collapse>
        </TableCell>
      </TableRow>


    </>
  )
}
async function searchClassroom(date){
    // console.log(date)
    // date= `${date.getFullYear()}/${(date.getMonth()+1)}/${date.getDate()}`;
    return  await axios.post('http://localhost:8080/api/classroom/booked', {date}, { headers: {'content-type': 'application/json',Authorization:authHeader().Authorization}})
    .then((data)=>{return data.data})
}
function formList(classroomList){
    console.log(classroomList)
    let classroomMap = new Map()
    for(var i =0; i<classroomList.length; i++){
        var buildingRoom = classroomMap.get(classroomList[i].classroom.buildingName) || []
        buildingRoom.push([classroomList[i].classroom,classroomList[i].periodList])
        classroomMap.set(classroomList[i].classroom.buildingName,buildingRoom)
    }
    console.log(classroomMap)
    return classroomMap     
    
}
function filter(classroomList,time){
  
  const result=[...classroomList.entries()].map((classrooms)=>(
    classrooms[1].filter((pair)=>(pair[1][time]===0)).map((pair)=>({classroom:pair[0],periodList:pair[1]}))
      
  ))
    return result.flat()
}
export default function BookedClassroom(){
    const [date, setDate] = useState( moment(new Date()).format('YYYY-MM-DD'));
    const [time, setTime] = useState(0);
    const [loading, setLoading] = useState(false);
    const [classroomList,setClassroomList] = useState(new Map());
    const fetchData = async () => {
        const classroomList = await Promise.all(searchClassroom(date))
        return classroomList
      }
      document.addEventListener("DOMContentLoaded", (event) => {
        alert("DOM fully loaded and parsed");
      });
    useEffect(()=>{
        setLoading(true)
        searchClassroom(date).then((data)=>{
            setClassroomList(formList(data))
            setLoading(false)
        })
        
    },[date])

    useEffect(()=>{
      setClassroomList(formList(filter(classroomList,time)))
    }, [time])
    
    return (
        <>
        {loading?
        <Box
          paddingTop={20}
          display="flex"
          justifyContent="center"
          alignItems="center"
        >
          <CircularProgress size={100} />
        </Box>
        :
        (
          <>
          <LocalizationProvider dateAdapter={AdapterDayjs}>
              <DemoContainer components={['DatePicker','desktopTimePicker']}>
                <DemoItem>
                  <DatePicker
                      disablePast = {true}
                      value = {dayjs(date+1)}
                      shouldDisableDate={(date)=>{
                          return date.date()>new Date().getDate()+7}}
                      onChange={(newDate) => {
                        console.log(newDate)
                        setDate(newDate)}}
                  />
                </DemoItem>
                <DemoItem>
                  <DesktopTimePicker  
                    // timeSteps={{minutes:30}}
                    label="時段"
                    ampm={false}
                    minTime={moment("9:00", "HH:mm")}
                    maxTime={moment("21:00", "HH:mm")}
                    views={["hours"]}
                    format='HH:MM'
                    // defaultValue={dayjs("0000-00-00T9:00")}
                    onChange={(newTime)=>{
                      console.log(newTime)
                      console.log(newTime.get('hour'))
                      setTime(newTime.get('hour'))}}
                  />

                </DemoItem>
              </DemoContainer>
              
          </LocalizationProvider>
          <TableBody>
          {/* {classroomList.forEach(function(value, key){
              <Row building = {value} classroomList = {key} />
          })} */}
          {
              [...classroomList.entries()].map((pair)=>{
                if(pair[0]==="共同"){
                  return <Row buildingName = {pair[0]} classrooms = {pair[1]} desiredTime = {time} />
                }
                  
              })
          }
          </TableBody>
          </>
        )
      }
        </>
    )
}