import { useNavigate, useParams } from "react-router-dom";
import { fetchExercisesById } from "../services/ExerciseService";
import { useEffect, useState } from "react";
import "../components/ExerciseDetail.scss"
import { submitExercise } from "../services/SubmissionService";
import { toast } from "react-toastify";
import ReactLoading from 'react-loading';
import MessageSocket from "./MessageSocket";
import MessageBar from "./MessageBar";


const ExerciseDetail = () => {
    const { exerciseId } = useParams();
    const nav = useNavigate();
    const [exercise, setExercise] = useState("");
    const [file, setFile] = useState("");
    const [fileName, setFileName] = useState("");
    const [language, setLanguage] = useState("1");
    const [isLoading, setIsLoading] = useState(false);
    const [submitButton, setSubmitButton] = useState(false);
    const handleFile = (e) => {
        setFile(e.target.files[0]);
        setFileName(e.target.files[0] ? e.target.files[0].name : "");
    }
    const getExerciseByID = async () => {
        try {
            const res = await fetchExercisesById(exerciseId);
            if (res && res.data) {
                setExercise(res.data);
                console.log(res.data);
            }
        } catch (error) {
            console.error(error);
        }
    }

    const submitFile = async () => {
        setSubmitButton(true);
        if (fileName === "") {
            // toast("Wow so easy!", {
            //     position: "top-right",
            //     autoClose: 5000,
            //     hideProgressBar: false,
            //     closeOnClick: true,
            //     pauseOnHover: true,
            //     draggable: true,
            //     progress: undefined,
            // });

            toast.error("File is invalid!", {
                autoClose: 2000
            })
        }
        else {
            setIsLoading(true);
            try {
                const res = await submitExercise(exerciseId, file, language);

                if (res && res.data) {
                    toast.success("Submit successfully!", {
                        autoClose: 2000
                    })
                    // nav("/history");
                    setIsLoading(false);
                    console.log(res.data);
                }
            } catch (error) {
                setIsLoading(false);
                console.log(error);
            }
        }
    }

    useEffect(() => {
        getExerciseByID()
        console.log(fileName);
    }, [])


    return (
        <>

            <MessageBar />
            <div className="exercise-detail" style={isLoading ? { 'opacity': '40%' } : { 'opacity': '100%' }}>
                {isLoading ? (
                    <ReactLoading className="loading" type="spinningBubbles" color='#000' height={'4%'} width={'4%'} />
                ) : ""}
                <h4>{exercise.name}</h4>
                <div className="detail">
                    <pre>{exercise.detail}</pre>
                </div>
                <div className="limited">
                    <span>Time limit: {exercise.timeLimit}s</span>
                    <span>Memory limit: {exercise.memoryLimit}KB</span>
                </div>

                <div className="submit-file">
                    <div className="option-language">
                        <span>Trình biên dịch:</span>
                        <select className="form-select" onChange={(e) => setLanguage(e.target.value)}>
                            <option value='1'>C</option>
                            <option value='2'>C++</option>
                            <option value='3'>Java</option>
                            <option value='4'>Python</option>
                        </select>
                    </div>

                    <div className="upload-file">
                        <input id="file" accept=".c, .cpp, .java, .py" type="file" className="form-control" hidden onChange={(e) => handleFile(e)}></input>

                        <input type="text" className="form-control" readOnly value={fileName}></input>
                        <label for="file" className="btn">
                            Chọn File
                        </label>
                    </div>
                </div>
                <input className="btn-submit btn" type="button" value="Nộp bài" onClick={submitFile}></input>
            </div>
            {/* 
            <MessageSocket /> */}
        </>

    )
}
export default ExerciseDetail;