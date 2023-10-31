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
import CircularProgress from '@mui/material/CircularProgress';
import { DesktopTimePicker } from '@mui/x-date-pickers/DesktopTimePicker';
import dayjs from 'dayjs'
import moment from 'moment/moment'
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';

function BookedPrompt(props){
  const {roomName,selectedBtn} = props
  const [open, setOpen] = useState(false);
  console.log(props)
  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  return (
    <Box textAlign="right">
      <Button variant="outlined" onClick={handleClickOpen} id={roomName+"btn"} hidden>
        Submit
      </Button>
      <Dialog
        open={open}
        onClose={handleClose}
        aria-labelledby="alert-dialog-title"
        aria-describedby="alert-dialog-description"
      >
        <DialogTitle id="alert-dialog-title">
          以下為預約資訊
        </DialogTitle>
        <DialogContent>
          <DialogContentText>
            教室：{selectedBtn.classroomName}
          </DialogContentText>   
          <DialogContentText>
            預約時段：{selectedBtn.periodList[0]}:00 - {selectedBtn.periodList[selectedBtn.periodList.length-1]}:00
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose}>取消</Button>
          <Button onClick={handleClose} autoFocus>
          確定，下一步
          </Button>
        </DialogActions>
      </Dialog>
    </Box>
  );
}
function ClassroomBlock(props){
  const {classroom, periodList, selectBtn, selectedBtn} = props
  console.log(props)

  return(
      <>
          <TableRow >
              <TableCell  width="100%" >{classroom.roomName}</TableCell>
          </TableRow>
          <TableCell sx={{ borderBottom: "none"}} >
              {periodList.map((period,index)=>{
                  if(period===0 && index>=8 && index<=21){
                    return(<Button variant='outlined' style={{ margin: 2 }} id={classroom.roomName+":"+index} onClick={()=>{selectBtn(classroom.roomName,index)}}>{index}:00</Button>)
                  }
                  else if(period===1){
                    return (<Button variant='outlined' style={{ margin: 2 }} disabled>{index}:00</Button>)
                  }
                  return ""
                        })}
          </TableCell>
          <BookedPrompt roomName = {classroom.roomName} selectedBtn = {selectedBtn} />
      </>
  )
}

function Row(props){
  const [open, setOpen] = useState(false)
  const {buildingName, classrooms, desiredTime, selectBtn, selectedBtn} = props
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
                  return <ClassroomBlock classroom = {pair[0]} periodList = {pair[1]} selectBtn = {selectBtn} selectedBtn = {selectedBtn}/>
                }
                else{
                  if(pair[1][desiredTime]===0){
                    return <ClassroomBlock classroom = {pair[0]} periodList = {pair[1]} selectBtn = {selectBtn} selectedBtn = {selectedBtn}/>
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
    const [selectedBtn, setSelectedBtn] = useState({
      classroomName:"",
      periodList:[]
    })
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

    function selectBtn(classroomName,period){
      if(selectedBtn.classroomName===""){
        document.getElementById(classroomName+":"+period).style.backgroundColor="#66B3FF"
        document.getElementById(classroomName+"btn").hidden=false
        setSelectedBtn({
          classroomName:classroomName,
          periodList:[period]
        })
      }
      else if(classroomName === selectedBtn.classroomName){
        if (selectedBtn.periodList.includes(period)){
          if(selectedBtn.periodList.length===3 && period===selectedBtn.periodList[1]){
            alert("時段需要連續")
          }
          else{
            const i = selectedBtn.periodList.indexOf(period) 
            selectedBtn.periodList.splice(i,1)
            document.getElementById(classroomName+":"+period).style.backgroundColor=""
            document.getElementById(classroomName+"btn").hidden=(selectedBtn.periodList.length===0)?true:false
            setSelectedBtn({
              classroomName:(selectedBtn.periodList.length===0)?"":classroomName,
              periodList:selectedBtn.periodList
            }) 
          }
        }
        else{
          if(selectedBtn.periodList.length===3){
            alert("只能選三個連續時段")
          }
          else{
            if(selectedBtn.periodList.includes(period-1)||selectedBtn.periodList.includes(period+1)){
              document.getElementById(classroomName+":"+period).style.backgroundColor="#66B3FF"
              setSelectedBtn({
                classroomName:classroomName,
                periodList:[...selectedBtn.periodList, period].sort()
              })
            }
            else{
              alert("請選擇連續的時段")
            }
          }
        }
        
      }
      else{
        alert("請選擇相同教室")
      }
    }
    
    useEffect(()=>{
      console.log(selectedBtn)
    }, [selectedBtn])

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
              [...classroomList.entries()].map((pair)=>(
                  <Row buildingName = {pair[0]} classrooms = {pair[1]} desiredTime = {time} selectBtn={selectBtn} selectedBtn = {selectedBtn}/>
              ))
          }
          </TableBody>
          </>
        )
      }
        </>
    )
}