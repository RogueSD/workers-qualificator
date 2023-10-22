import React, { useEffect, useState } from "react";
import styles from "./style.module.scss";
import { ShortWorker, getWorkersPage } from "../../../models/worker";
import Worker from "../../Worker/Worker";
import axios from "axios";

const MainPage = () => {
  const [workers, setWorkers] = useState<ShortWorker[]>([]);

  useEffect(() => {
    const fetchWorkers = async () => {
      const response = await getWorkersPage();
      setWorkers(response.data.content);
    };

    fetchWorkers();
  }, []);

  const downloadFile = async () => {
    try {
      const response = await axios.get("api/integration/export", {
        responseType: "blob",
      });
      const url = window.URL.createObjectURL(new Blob([response.data]));
      const link = document.createElement("a");
      link.href = url;
      link.setAttribute("download", "file.xlsx");
      document.body.appendChild(link);
      link.click();
      link.remove();
    } catch (error) {
      console.error("Failed to download file");
    }
  };

  return (
    <>
      <div>
        <button onClick={downloadFile}>Download File</button>
      </div>
      <div className={styles.header}>
        <div>ID</div>
        <div>Работник</div>
        <div>Квалифицирован?</div>
      </div>
      {workers.map((worker) => (
        <Worker worker={worker} key={worker.id} />
      ))}
    </>
  );
};

export default MainPage;
