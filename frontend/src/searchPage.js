import KeyboardArrowDownIcon from '@mui/icons-material/KeyboardArrowDown'
import KeyboardArrowUpIcon from '@mui/icons-material/KeyboardArrowUp'
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
import axios from 'axios';
import authHeader from './services/auth-header';
import { useEffect, useState } from 'react'
import { NavLink } from 'react-bootstrap'

function Row (props) {
  const { row } = props
  const event = row[0]
  const myParticipation = row[1]
  // 設定使用者下拉式選單開闔
  const [open, setOpen] = useState(false)

  const btn =(eventId,holderName,myParticipation)=>{
    const user = JSON.parse(localStorage.getItem("user"))
    console.log(holderName)
    if (user){
      if (user.username === holderName){
        return "您為活動主辦人"
      }
    }
    
    if (myParticipation.includes(eventId)){
      return "已參加"
    }
    else{
      return (<Button onClick={() => { joinEvent(event.eventId) }}>參加活動</Button>)
    }
    

  }
  return (
    <>
      <TableRow sx={{ borderBottom: 1 }} id={'eventId' + event.eventId}>
        <TableCell>
          <IconButton
            aria-label='expand row'
            size='small'
            onClick={() => setOpen(!open)}
          >
            {open ? <KeyboardArrowUpIcon /> : <KeyboardArrowDownIcon />}
          </IconButton>
        </TableCell>
        {/* 使用者資料 */}
        <TableCell align='right'>{event.eventName}</TableCell>
        <TableCell align='right'>{event.eventDate}</TableCell>
        
        <TableCell align='right'>{event.eventLocation}</TableCell>
        <TableCell align='right'>{event.max}</TableCell>
        {/* <TableCell align='right'><Button onClick={() => { joinEvent(event.eventId) }}>參加活動</Button></TableCell> */}
        <TableCell align='right'>{btn(event.eventId,event.holderName,myParticipation)}</TableCell>
      </TableRow>
      <TableRow>
        <TableCell style={{ paddingBottom: 1, paddingTop: 0, margin: 2 }} colSpan={6}>
          <Collapse in={open} timeout='auto' unmountOnExit>
            <Box sx={{ margin: 1 }}>
              
              {/* <TableCell> */}
                <Typography variant='h6' gutterBottom component='div'>
                  活動內容

                </Typography>
                <TextField 
                value={event.content}
                multiline
                fullWidth 
                InputProps={{
                    readOnly: true,
                }} />
                {/* <TableCell align='right'>{row.content}</TableCell> */}
              {/* </TableCell> */}
            </Box>
          </Collapse>
        </TableCell>
      </TableRow>


    </>
  )
}


async function searchEvent () {
  return await axios.get('http://localhost:8080/api/events?status=in_progress', { headers: authHeader() })
    .then((data) => { return data.data })
}
async function SearchParticipation () {
  return await axios.get('http://localhost:8080/api/joins', { headers: authHeader() })
    .then((data) => { return data.data })
}

async function joinEvent (id) {
  if (window.confirm('確定要參加？')) {
    const url = 'http://localhost:8080/api/joins'
    await axios.post(url,{id},{ headers: authHeader() }).then((data) => {
      console.log(data)
    })
  }
}

async function formEvent (event) {
  var eventDate;
  if (event.date.includes("T")){
    eventDate = event.date.split("T")[0]+" "+event.date.split("T")[1].split(".")[0]
  
  }
  else{
    eventDate = event.date
  }
  return {
    eventId: event.id,
    eventName: event.eventName,
    eventLocation: event.location,
    eventDate: eventDate,
    max: event.max,
    content: event.content,
    holderName: event.userName
  }
}

async function formParticipation (participation) {
  
  return participation.event.id
}

export default function SearchPage () {
  const [form, setForm] = useState([])
  const [myParticipation, setMyParticipation] = useState([])
  const fetchData = async () => {
    const events = await searchEvent()
    // const participations = await SearchParticipation()
    console.log(events)
    const eventList = await Promise.all(events.map((event) => (formEvent(event))))
    return eventList
  }
  useEffect(() => {
    fetchData().then((res) => {
      setForm(res)
    })
      .catch((e) => {
        console.log(e.message)
      })
  }, [])

  const fetchJoin = async () => {
    const participations = await SearchParticipation()
    console.log(participations)
    const participationList = await Promise.all(participations.map((event) => (formParticipation(event))))
    return participationList
  }
  useEffect(() => {
    fetchJoin().then((res) => {
      console.log(res)
      setMyParticipation(res)
    })
      .catch((e) => {
        console.log(e.message)
      })
  }, [])

  return (
    <>
      <TableContainer component={Paper}>
        <Table aria-label='collapsible table'>
          <TableHead>
            <TableRow>
              <TableCell />
              <TableCell align='right'>活動名稱</TableCell>
              <TableCell align='right'>活動時間</TableCell>
              <TableCell align='right'>活動地點</TableCell>
              <TableCell align='right'>活動人數上限</TableCell>
              <TableCell align='right' />
            </TableRow>
          </TableHead>
          <TableBody>
            {form.map((event)=><Row key={event.eventId} row={[event,myParticipation]} />)}
          </TableBody>
        </Table>
      </TableContainer>
    </>
  )
}
