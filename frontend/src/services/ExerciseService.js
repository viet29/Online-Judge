import axios from "axios"

const fetchAllExercises = (topicId) => {
    return axios.get("http://localhost:8080/exercises/", {
        params: {
            'topicId': topicId
        }
    });
}

const fetchExercisesById = (exerciseId) => {
    return axios.get("http://localhost:8080/exercises/" + exerciseId);
}
export { fetchAllExercises, fetchExercisesById };