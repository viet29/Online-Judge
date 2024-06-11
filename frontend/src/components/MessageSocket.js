import { useEffect, useState } from "react";
import SockJS from "sockjs-client";
import { Stomp } from "@stomp/stompjs"
import "../components/MessageSocket.scss"

const MessageSocket = ({ submitButton }) => {
    const [messages, setMessages] = useState([]);

    useEffect(() => {
        console.log(submitButton);
        if (submitButton === true) {
            setMessages([]);
        }
    }, [])

    useEffect(() => {
        let stompClient = null;

        const connect = () => {
            const socket = new SockJS('http://localhost:8082/ws');
            stompClient = Stomp.over(socket);

            stompClient.connect({}, (frame) => {
                console.log("Connected: " + frame);
                stompClient.subscribe('/topic/notification', (noti) => {
                    const message = JSON.parse(noti.body);
                    console.log("Received: " + message.detail);

                    setMessages(prevMessage => [...prevMessage, message]);


                });
            }, (error) => {
                console.error("Connection error: " + error);
                // setTimeout(connect, 5000); // retry connection after 5 seconds
            });
        };

        connect();

        return () => {
            if (stompClient) {
                stompClient.disconnect();
                console.log("Disconnected");
            }
        };
    }, []);
    return (
        <>
            <div className="message">
                <h4>Message</h4>
                {messages && messages.map((message, index) => {
                    return (
                        <p key={index}>
                            <i className={message.title === "Thành công" ? "fa-solid fa-circle-check" : "fa-solid fa-circle-exclamation"}
                                style={{ "color": message.title === "Thành công" ? "green" : "red" }}
                            ></i>
                            <span>Step {message.step}: </span>
                            <span>{message.detail}</span>
                        </p>
                    )
                })}
            </div>
        </>
    )
}

export default MessageSocket;