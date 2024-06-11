import { Container } from 'react-bootstrap';
import './App.scss';
import ListExercises from './components/ListExercises';
import ExerciseDetail from './components/ExerciseDetail';
import { Route, Routes } from 'react-router-dom';
import Header from './layouts/Header';
import Footer from './layouts/Footer';
import HistorySubmission from './components/HistorySubmission';
import SubmissionDetail from './components/SubmissionDetail';
import 'react-toastify/dist/ReactToastify.css';
import { ToastContainer } from 'react-toastify';
import { AnimatePresence, motion } from 'framer-motion';



function App() {
  return (
    <>
      <div className="app-container">
        <motion.div className="app-container"
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          exit={{ opacity: 0 }}
        >
          <AnimatePresence>
            <Header />
            <Container>
              <ToastContainer />
              <Routes>
                <Route path='/' element={<ListExercises />} />
                <Route path='/history' element={<HistorySubmission />} />
                <Route path="/exercises/:exerciseId" element={<ExerciseDetail />} />
                <Route path="/history/:id" element={<SubmissionDetail />} />
              </Routes>
            </Container>
          </AnimatePresence>
        </motion.div>
      </div>
      {/*<ToastContainer*/}
      {/*    position="top-right"*/}
      {/*    autoClose={2000}*/}
      {/*    hideProgressBar={false}*/}
      {/*    newestOnTop={false}*/}
      {/*    closeOnClick*/}
      {/*    rtl={false}*/}
      {/*    pauseOnFocusLoss*/}
      {/*    draggable*/}
      {/*    pauseOnHover*/}
      {/*    theme="light"*/}
      {/*/>*/}
    </>
  );
}

export default App;
