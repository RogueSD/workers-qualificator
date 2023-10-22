import React from "react";
import Header from "./components/Header/Header";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import MainPage from "./components/Pages/MainPage/MainPage";
import DetailPage from "./components/Pages/WorkerPage/WorkerDetailPage";

const App = () => {
  return (
    <div className="App">
      <BrowserRouter>
        <Header />
        <Routes>
          <Route path={'/'} element={ <MainPage /> }/>
          <Route path={'/worker/:id'} element={ <DetailPage/> }/>
        </Routes>
      </BrowserRouter>
    </div>
  );
};

export default App;