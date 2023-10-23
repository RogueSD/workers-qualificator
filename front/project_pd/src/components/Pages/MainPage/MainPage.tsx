import React, { useEffect, useState } from "react";
import styles from "./style.module.scss";
import { LongWorker, ShortWorker, createWorker, getWorkersPage } from "../../../models/worker";
import axios from "axios";
import Modal from "../../UI/ModalCreate/ModalCreate";
import Worker from "../../Worker/Worker";

const MainPage = () => {
  const [workers, setWorkers] = useState<ShortWorker[]>([]);

  // Получение всех работников
  const fetchWorkers = async () => {
    const response = await getWorkersPage();
    setWorkers(response.data.content);
  };

  useEffect(() => {
    fetchWorkers();
  }, []);

  // Сохранение xlsx-файла
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

  // Модалка для добавления работников
  const [isModalOpen, setIsModalOpen] = useState(false);
  const openModal = () => {
    setIsModalOpen(true);
  };
  const closeModal = () => {
    setIsModalOpen(false);
  };

  // Добавление нового работника
  const handleAddWorker = (newWorker: LongWorker) => {
    createWorker(newWorker)
      .then(() => {
        fetchWorkers();
        closeModal();
      })
      .catch((error) => {
        console.log('Произошла ошибка при добавлении работника');
      });
  };

  return (
    <>
      <Modal isOpen={isModalOpen} onClose={closeModal} onAdd={handleAddWorker}/>
      <div className={styles.menuBar}>
        <button onClick={openModal} className={styles.button}>Добавить работника</button>
        <button onClick={downloadFile} className={styles.button}>Скачать xlsx-файл</button>
      </div>
      <div className={styles.header}>
        <div className={styles.left}>ID</div>
        <div className={styles.center}>Работник</div>
        <div className={styles.right}>Квалифицирован?</div>
      </div>
      {workers.map((worker) => (
        <Worker worker={worker} key={worker.id}/>
      ))}
    </>
  );
};

export default MainPage;
