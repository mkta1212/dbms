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
import authHeader from '../services/auth-header';
import { useEffect, useState } from 'react'
import { NavLink } from 'react-bootstrap'

function Row (props) {
  const { row } = props
  // 設定使用者下拉式選單開闔
  const [open, setOpen] = useState(false)


  return (
    <>
      <TableRow sx={{ borderBottom: 1 }} id={'eventId' + row.eventId}>
        <TableCell>
          <IconButton
            aria-label='expand row'
            size='small'
            onClick={() => setOpen(!open)}
          >
            {open ? <KeyboardArrowUpIcon /> : <KeyboardArrowDownIcon />}
          </IconButton>
        </TableCell>
        <TableCell align='right'>{row.eventName}</TableCell>
        <TableCell align='right'>{row.eventDate}</TableCell>
        
        <TableCell align='right'>{row.eventLocation}</TableCell>
        <TableCell align='right'>{row.max}</TableCell>
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
                value={row.content}
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


async function SearchEvent () {
  return await axios.get('http://localhost:8080/api/myevents', { headers: authHeader() })
    .then((data) => { return data.data })
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
    eventStatus: event.eventStatus,
    max: event.max,
    content: event.content,
  }
}

export default function MyEvents () {
  const [form, setForm] = useState([])
  const [in_progress,set_in_progress] = useState(false)
  const [finished,set_finished] = useState(false)

  const fetchData = async () => {
    const events = await SearchEvent()
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

  return (
    <>
        <IconButton id = "in_progress_btn"
            onClick={() => {
            set_in_progress(true)
            set_finished(false)
            document.getElementById("in_progress_btn").style.color = "red"
            document.getElementById("in_progress_btn").style.fontWeight = "bolder"
            document.getElementById("finished_btn").style.color= "grey"
            document.getElementById("finished_btn").style.fontWeight= "normal"}}>
            進行中
        </IconButton>
        <IconButton id = "finished_btn"
            onClick={() => {
            set_in_progress(false)
            set_finished(true)
            document.getElementById("finished_btn").style.color = "red"
            document.getElementById("finished_btn").style.fontWeight = "bolder"
            document.getElementById("in_progress_btn").style.color= "grey"
            document.getElementById("in_progress_btn").style.fontWeight= "normal"}}>
            已結束
        </IconButton>
        <TableContainer component={Paper}>
            
        
        <Table aria-label='collapsible table'>
          <TableHead>
            <TableRow>
                
              <TableCell />
              <TableCell align='right'>活動名稱</TableCell>
              <TableCell align='right'>活動時間</TableCell>
              <TableCell align='right'>活動地點</TableCell>
              <TableCell align='right'>活動人數上限</TableCell>
              {/* <TableCell align='right' /> */}
            </TableRow>
          </TableHead>
          <TableBody>
            {in_progress && form.filter(event =>event.eventStatus==="In_Progress").map((event)=><Row key={event.eventId} row={event} />)}
            {finished && form.filter(event =>event.eventStatus==="Finished").map((event)=><Row key={event.eventId} row={event} />)}
          </TableBody>
        </Table>
      </TableContainer>
    </>
  )
}
