import axios from "axios"

const submitExercise = (code, file, language) => {
    const formData = new FormData();
    formData.append('file', file);
    formData.append('language', language);
    return axios.post(`http://localhost:8080/submit/${code}`, formData);
}

const fetchAllSubmissions = () => {
    return axios.get(`http://localhost:8080/submissions`);
}

const fetchSubmissionById = (id) => {
    return axios.get(`http://localhost:8080/submissions/${id}`);
}

export { submitExercise, fetchAllSubmissions, fetchSubmissionById };