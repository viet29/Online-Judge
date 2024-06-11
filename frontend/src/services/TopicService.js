import axios from "axios"

const fetchAllTopics = () => {
    return axios.get("http://localhost:8080/topics");
}
export { fetchAllTopics };