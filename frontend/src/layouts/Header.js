import "../layouts/Header.scss"
const Header = () => {
    return (
        <>
            <div className="header">
                <div className="left">
                    <a href="/" className="logo">Code Run</a>
                </div>
                <div className="right">
                    <a href='/'className="exercise-nav" >Bài tập</a>
                    <a href='/history' className="history-nav">Lịch sử</a>
                    <a href='/guide' className="guide">Hướng dẫn</a>
                    <a className="login-nav">Đăng xuất</a>
                    <span className="user-info">
                        <i class="fa-solid fa-user"></i>
                    </span>
                </div>
            </div>
        </>
    )
}
export default Header;