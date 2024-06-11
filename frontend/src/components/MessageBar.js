import React, { useEffect, useState } from "react";
import "./MessageBar.scss";
import SockJS from "sockjs-client";
import { Stomp } from "@stomp/stompjs";

const MessageBar = () => {
    const [messages, setMessages] = useState([]);

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
                    if (message.step === 1) {
                        setMessages([message]);
                    } else {
                        setMessages(prevMessage => [...prevMessage, message]);
                    }

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
        // <div className="bar">
        //     {messages.map((message, index) => (
        //         <React.Fragment key={index}>
        //             <div className="step">
        //                 <div className={`step-circle`} style={{backgroundColor: (message.title === "Thành công") ? 'green' : 'red'}}></div>
        //                 <span>{message.detail}</span>
        //             </div>
        //             {index < messages.length - 1 && <div className="line"></div>}
        //         </React.Fragment>
        //     ))}
        // </div>
        <div className="ms">
            <h2>Thanh tiến trình</h2>
            <div className="bar">
                <div className="step">
                    <div className="step-circle" style={{ backgroundColor: messages.length < 1 ? "#B8B8B8" : (messages[0]?.title === "Thành công" ? 'green' : 'red') }} >
                        <i className={messages.length < 1 ? "" : (messages[0]?.title === "Thành công" ? "fa-solid fa-circle-check" : "fa-solid fa-circle-exclamation")} />
                    </div>
                    {messages.length >= 1 && (
                        <span>{messages[0]?.detail}</span>
                    )}
                </div>
                <div className="line" style={{ backgroundColor: messages.length < 2 ? "#B8B8B8" : (messages[1]?.title === "Thành công" ? 'green' : 'red') }}></div>
                <div className="step">
                    <div className="step-circle" style={{ backgroundColor: messages.length < 2 ? "#B8B8B8" : (messages[1]?.title === "Thành công" ? 'green' : 'red') }}>
                        <i className={messages.length < 2 ? "" : (messages[1]?.title === "Thành công" ? "fa-solid fa-circle-check" : "fa-solid fa-circle-exclamation")} />
                    </div>
                    {messages.length >= 2 && (
                        <span>{messages[1]?.detail}</span>
                    )}
                </div>
                <div className="line" style={{ backgroundColor: messages.length < 3 ? "#B8B8B8" : (messages[2]?.title === "Thành công" ? 'green' : 'red') }}></div>
                <div className="step" >
                    <div className="step-circle" style={{ backgroundColor: messages.length < 3 ? "#B8B8B8" : (messages[2]?.title === "Thành công" ? 'green' : 'red') }}>
                        <i className={messages.length < 3 ? "" : (messages[2]?.title === "Thành công" ? "fa-solid fa-circle-check" : "fa-solid fa-circle-exclamation")} />
                    </div>
                    {messages.length >= 3 && (
                        <span>{messages[2]?.detail}</span>
                    )}
                </div>
                <div className="line" style={{ backgroundColor: messages.length < 4 ? "#B8B8B8" : (messages[3]?.title === "Thành công" ? 'green' : 'red') }}></div>
                <div className="step">
                    <div className="step-circle" style={{ backgroundColor: messages.length < 4 ? "#B8B8B8" : (messages[3]?.title === "Thành công" ? 'green' : 'red') }}>
                        <i className={messages.length < 4 ? "" : (messages[3]?.title === "Thành công" ? "fa-solid fa-circle-check" : "fa-solid fa-circle-exclamation")} />
                    </div>
                    {messages.length >= 4 && (
                        <span>{messages[3]?.detail}</span>
                    )}
                </div>
            </div>
        </div>
    );
};

export default MessageBar;