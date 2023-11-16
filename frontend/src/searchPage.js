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
import Pagination from '@mui/material/Pagination';
import PaginationItem from '@mui/material/PaginationItem';
import Stack from '@mui/material/Stack';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import ArrowForwardIcon from '@mui/icons-material/ArrowForward';
import MenuItem from '@mui/material/MenuItem';
import Select from '@mui/material/Select';
import CircularProgress from '@mui/material/CircularProgress';
import FormControl from '@mui/material/FormControl';


function Row (props) {
  const { row } = props
  const event = row[0]
  const myParticipation = row[1]
  console.log(event)
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
        <TableCell align='right'>{event.courseName}</TableCell>
        <TableCell align='right'>{event.instructorName}</TableCell>
        <TableCell align='right'>{event.eventDate}</TableCell>
        <TableCell align='right'>{event.periodList[0]}:00 - {event.periodList[event.periodList.length-1]+1}:00</TableCell>
        <TableCell align='right'>{event.roomName}</TableCell>
        {/* <TableCell align='right'>{event.max}</TableCell> */}
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


async function searchEvent (page, row) {
  console.log(row)
  return await axios.get('http://localhost:8080/api/studyEvents', { 
    params:{
      page:page,
      row:row,
    },
    headers: authHeader() })
    .then((data) => { return data.data })
}
async function SearchParticipation () {
  return await axios.get('http://localhost:8080/api/joins', { headers: authHeader() })
    .then((data) => { return data.data })
}

async function joinEvent (eventId) {
  if (window.confirm('確定要參加？')) {
    const url = 'http://localhost:8080/api/joins'
    await axios.post(url,{eventId},{ headers: authHeader() }).then((data) => {
      console.log(data)
    })
  }
}

async function formEvent (event) {
  var periodList = JSON.parse("[" + event.periodList + "]");
  return {
    courseName: event.courseName,
    instructorName: event.instructorName,
    roomName: event.roomName,
    eventDate: event.eventDate,
    max: event.max,
    content: event.content,
    periodList: periodList
  }
}

async function formParticipation (participation) {
  
  return participation.event.id
}

export default function SearchPage () {
  const [loading, setLoading] = useState(false);
  const [form, setForm] = useState([])
  const [myParticipation, setMyParticipation] = useState([])
  const [page, setPage] = useState(0)
  const [row, setRow] = useState(10)
  const [totalPage, setTotalPage] = useState(0)
  const fetchData = async (page, row) => {
    const response = await searchEvent(page, row)
    // const participations = await SearchParticipation()
    console.log(response)
    const totalPage = response.totalPage
    const events=  response.eventDTO
    setTotalPage(totalPage)
    const eventList = await Promise.all(events.map((event) => (formEvent(event))))
    return eventList
  }
  useEffect(() => {
    setLoading(true)
    fetchData(page,row).then((res) => {
      setForm(res)
      setLoading(false)
    })
      .catch((e) => {
        console.log(e.message)
      })
  }, [page,row])

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
          
          <TableContainer component={Paper}>
            <Table aria-label='collapsible table'>
              <TableHead>
                <TableRow>
                  <TableCell />
                  <TableCell align='right'>課程名稱</TableCell>
                  <TableCell align='right'>授課老師</TableCell>
                  <TableCell align='right'>活動日期</TableCell>
                  <TableCell align='right'>活動時段</TableCell>
                  <TableCell align='right'>活動地點</TableCell>
                  <TableCell align='right'></TableCell>
                  {/* <TableCell align='right' /> */}
                  <TableCell align='right'>
                  <FormControl sx={{ m: 1}} size="small">
                  
                    <Select
                      autoWidth={true}
                      value={row}
                      onChange={(value)=>setRow(value.target.value)}
                      sx={{width:80}}
                    >
                      <MenuItem value={10} >10</MenuItem>
                      <MenuItem value={25}>25</MenuItem>
                      <MenuItem value={50}>50</MenuItem>
                    </Select>
                    
                  </FormControl>
                  </TableCell>
                  
                </TableRow>
              </TableHead>
              <TableBody>
                {form.map((event)=><Row key={event.eventId} row={[event,myParticipation]} />)}
              </TableBody>
            </Table>
            <Box display='flex' justifyContent='center'>
            <Stack spacing={2}>
              <Pagination
                count={totalPage}
                onChange={(event,num)=>setPage(num)}
                page={page+1}
                renderItem={(item) => (
                  <PaginationItem
                  
                    slots={{ previous: ArrowBackIcon, next: ArrowForwardIcon }}
                    {...item}
                  />
                )}
              />
            </Stack>
            </Box>
          </TableContainer>
        </>
        )
      }
    </>
  )
}
