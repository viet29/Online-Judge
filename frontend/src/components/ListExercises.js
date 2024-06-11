import { Table } from "react-bootstrap";
import "../components/ListExercises.scss"
import { useEffect, useState } from "react";
import { fetchAllExercises } from "../services/ExerciseService";
import { Link } from "react-router-dom";
import Select from "react-select"
import { useNavigate } from 'react-router-dom';
import { fetchAllTopics } from "../services/TopicService";
const ListExercises = () => {
    const [listExercises, setListExercises] = useState("");
    const [listTopics, setListTopics] = useState("");
    const nav = useNavigate();
    const getAllExercises = async () => {
        try {
            const res = await fetchAllExercises();
            if (res && res.data) {
                setListExercises(res.data);
                console.log(res.data);
            }
        } catch (error) {
            console.log(error);
        }
    }
    useEffect(() => {
        const getAllTopics = async () => {
            try {
                const res = await fetchAllTopics();
                if (res && res.data) {
                    setListTopics(res.data);
                    console.log(res.data);
                }
            } catch (error) {
                console.log(error);
            }
        }
        getAllExercises();
        getAllTopics();
    }, [])



    const handleFilter = async (id) => {
        if (id === "") {
            getAllExercises();
        } else {
            try {
                console.log(id);
                const res = await fetchAllExercises(Number(id));
                if (res && res.data) {
                    setListExercises(res.data);
                }
            } catch (error) {
                console.log(error);
            }
        }
    }
    return (
        <>
            <div className="list-exercises">
                <div className="title">
                    <h3>Bài Tập</h3>
                    <div className="search-and-filter">
                        <input className="form-control search" type="text" placeholder="Tìm kiếm bài tập ..."></input>
                        <select className="form-select" onChange={(e) => handleFilter(e.target.value)}>
                            <option value=''>Tất cả chủ đề</option>
                            {listTopics && listTopics.map((topic) => {
                                return (
                                    <option value={topic.id}>{topic.name}</option>
                                )
                            })}
                        </select>
                    </div>
                </div>
                <Table className="list" >
                    <thead>
                        <tr>
                            <th className="col-1">STT</th>
                            <th className="col-1">Mã</th>
                            <th className="col-5">Tiêu đề</th>
                            <th className="col-1">Submit</th>
                            {/* <th className="col-1">Đúng</th> */}
                        </tr>
                    </thead>
                    <tbody>
                        {listExercises && listExercises.map((exercise) => {
                            return (
                                <tr key={exercise.id}>
                                    <td>{exercise.id}</td>
                                    <td>
                                        <a href={'/exercises/' + exercise.code}>{exercise.code}</a>
                                    </td>
                                    <td>
                                        <a href={'/exercises/' + exercise.code}>{exercise.name}</a>
                                    </td>
                                    <td>{exercise.submissionList ? exercise.submissionList.length : 0}</td>

                                </tr>
                            )
                        })}
                    </tbody>
                </Table>
            </div>
        </>
    )
}
export default ListExercises;