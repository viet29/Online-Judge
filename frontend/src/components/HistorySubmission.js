import { Table } from "react-bootstrap";
import "../components/HistorySubmission.scss"
import { useEffect, useState } from "react";
import { fetchAllSubmissions } from "../services/SubmissionService";
import dayjs from "dayjs";
import { useNavigate } from "react-router-dom";
const HistorySubmission = () => {
    const nav = useNavigate();
    const [listSubmissions, setListSubmission] = useState([])
    useEffect(() => {
        const getAllSubmissions = async() => {
            try {
                const res = await fetchAllSubmissions();
                console.log(res.data);
                if (res && res.data) {
                    const ans = res.data.sort((a, b) =>{
                        return a.id > b.id ? -1 : 1;
                    })
                    setListSubmission(ans);
                    console.log(ans);
                }
            } catch (error) {
                console.log(error);
            }
        }

        getAllSubmissions();
    }, [])

    const formatDate = (d) =>{
        return d[2].toString().padStart(2, '0') + "/" + d[1].toString().padStart(2, '0') + "/" + d[0].toString().padStart(2, '0') + " " + d[3].toString().padStart(2, '0') + ":" + d[4].toString().padStart(2, '0') + ":" + d[5].toString().padStart(2, '0'); 
    }

    const navi = (submissionId) =>{
        nav("/history/" + submissionId);
    }
    return (
        <>
            <div className="submission-history">
                <h3>Lịch sử nộp bài</h3>
                <Table className="list">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Thời gian</th>
                            <th>Tài khoản</th>
                            <th>Bài tập		</th>
                            <th>Kết quả</th>
                            <th>Run Time</th>
                            <th>Bộ nhớ</th>
                            <th>Trình biên dịch</th>
                        </tr>
                    </thead>

                    <tbody>
                        {listSubmissions && listSubmissions.map((submission) => {
                            return (
                                <tr key={submission.id}>
                                    <td>{submission.id}</td>
                                    <td>{formatDate(submission.dateSubmit)}</td>
                                    <td>{submission.user ? submission.user.fullName : "Cao Xuân Trung"}</td>
                                    <td>{submission.exercise.name}</td>
                                    <td style={submission.status==='AC'? {color: 'green'} : {color: 'red'}}><a href={`/history/${submission.id}`}>{submission.status}</a></td>
                                    <td>{submission.actualTime ? submission.actualTime + "s" : "--"}</td>
                                    <td>{submission.actualMemory ? submission.actualMemory + "KB" : "--"}</td>
                                    <td>{submission.language}</td>
                                </tr>
                            )
                        })}
                    </tbody>
                </Table>
            </div>
        </>
    )
}

export default HistorySubmission;