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
import { DemoContainer } from '@mui/x-date-pickers/internals/demo';
import axios from 'axios';
import authHeader from '../services/auth-header';
import { useEffect, useState } from 'react'
import UserService from "../services/user.service";
import KeyboardArrowDownIcon from '@mui/icons-material/KeyboardArrowDown'
import KeyboardArrowUpIcon from '@mui/icons-material/KeyboardArrowUp'
import ToggleButton from '@mui/material/ToggleButton';
import ToggleButtonGroup from '@mui/material/ToggleButtonGroup';

import dayjs from 'dayjs'
function ClassroomBlock(props){
    const {classroom, periodList} = props
    console.log(props)
    return(
        <>
            <TableRow >
                <TableCell  width="100%" >{classroom.roomName}</TableCell>
            </TableRow>
            <TableCell sx={{ borderBottom: "none"}} >
                {periodList.map((period,index)=>{
                    if(period===0 && index>=8 && index<=21){
                      return(<Button variant='outlined' style={{ margin: 2 }}>{index}:00</Button>)
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
  const {buildingName, classrooms} = props
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
              
              {classrooms.map((pair)=>(<ClassroomBlock classroom = {pair[0]} periodList = {pair[1]}/>))}
            </Box>
          </Collapse>
        </TableCell>
      </TableRow>


    </>
  )
}
async function searchClassroom(date){
    console.log(new Date().getMonth())
    console.log(authHeader())
    return  await axios.get('http://localhost:8080/api/classroom/booked', { headers: authHeader()})
    .then((data)=>{return data.data})
}
function formList(classroomList){
    let classroomMap = new Map()
    for(var i =0; i<classroomList.length; i++){
        var buildingRoom = classroomMap.get(classroomList[i].classroom.buildingName) || []
        buildingRoom.push([classroomList[i].classroom,classroomList[i].periodList])
        classroomMap.set(classroomList[i].classroom.buildingName,buildingRoom)
    }
    return classroomMap     
    
}
export default function BookedClassroom(){
    const [date,setDate] = useState(new Date());

    const [classroomList,setClassroomList] = useState(new Map());
    const fetchData = async () => {
        const classroomList = await Promise.all(searchClassroom(date))
        return classroomList
      }
    useEffect(()=>{
        searchClassroom(date).then((data)=>{
            setClassroomList(formList(data))
        })
    },[])
    useEffect(()=>{
        console.log(classroomList)
    },[classroomList])
    console.log(classroomList)
    return (
        <>
        <LocalizationProvider dateAdapter={AdapterDayjs}>
            <DemoContainer components={['DatePicker']}>
                <DatePicker
                    label="Controlled picker"
                    disablePast = {true}
                    value = {dayjs(date+1)}
                    shouldDisableDate={(date)=>{
                        console.log(date.day())
                        return date.date()>new Date().getDate()+7}}
                    onChange={(newDate) => setDate(newDate)}
                />
            </DemoContainer>
        </LocalizationProvider>
        <TableBody>
        {/* {classroomList.forEach(function(value, key){
            <Row building = {value} classroomList = {key} />
        })} */}
        {
            [...classroomList.entries()].map((pair)=>(
                <Row buildingName = {pair[0]} classrooms = {pair[1]} />
            ))
        }
        </TableBody>
        </>
    )
}