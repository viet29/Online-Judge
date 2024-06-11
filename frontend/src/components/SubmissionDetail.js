import { useParams } from "react-router-dom";
import "../components/SubmissionDetail.scss"
import { fetchSubmissionById } from "../services/SubmissionService";
import { useEffect, useState } from "react";
const SubmissionDetail = () => {
    const { id } = useParams();
    const [submission, setSubmission] = useState({});
    const getSubmissionById = async () => {
        try {
            const res = await fetchSubmissionById(id);
            if (res && res.data) {
                setSubmission(res.data);
                console.log(res.data)
            }
        } catch (error) {
            console.log(error);
        }
    }

    useEffect(() => {
        getSubmissionById();
    }, [])
    return (
        <>
            {submission.exercise ? (
                <div className="submission-detail">
                    <h4>{submission.exercise.name}</h4>
                    <div className="language">
                        <span>Trình biên dịch: </span>
                        <span>{submission.language}</span>
                    </div>
                    <pre>{submission.content}</pre>
                </div>
            ) : ""}
        </>
    )
}
export default SubmissionDetail;