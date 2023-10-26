import React, { useEffect, useState } from "react";
import styles from "./style.module.scss";
import { LongWorker, getWorker } from "../../../models/worker";
import { useParams } from "react-router-dom";
import ListItem from "../../ListItem/ListItem";
import ModalUpdate from "../../UI/ModalUpdate/ModalUpdate";

const DetailPage = () => {
  const [worker, setWorker] = useState<LongWorker>();
  const { id } = useParams<{ id: any }>();

  // Получение работника по id
  const fetchWorker = async () => {
    const response = await getWorker(id);
    setWorker(response.data);
  };

  useEffect(() => {
    fetchWorker();
  }, []);

  const [isModalOpen, setIsModalOpen] = useState(false);
  const openModal = () => {
    setIsModalOpen(true);
  };
  const closeModal = () => {
    setIsModalOpen(false);
    fetchWorker();
  };

  return (
    <>
     <ModalUpdate isOpen={isModalOpen} onClose={closeModal} longWorker={worker}/>
      <div className={styles.root}>
        <div className={styles.buttons}>
          <button className={styles.button} onClick={() => window.history.back()}>Назад</button>
          <button className={styles.button} onClick={openModal}>Изменить</button>
        </div>
        <div className={styles.list}>
          <ListItem static={"Имя:"} dynamic={worker?.firstName} />
          <ListItem static={"Фамилия:"} dynamic={worker?.lastName} />
          <ListItem static={"Комментарий аудита:"} dynamic={worker?.auditComment} />
          <ListItem static={"Квалификация:"} dynamic={worker?.qualification?.qualificationName} />
          <ListItem static={"Количество произведенной продукции:"} dynamic={worker?.qualification?.manufacturedProductCount} />
          <ListItem static={"Процент бракованной продукции:"} dynamic={worker?.qualification?.defectiveProductsPercentage} />
          <ListItem static={"Специализация:"} dynamic={worker?.qualification?.specialization.specializationName} />
          <ListItem static={"Количество произведенных изделий:"} dynamic={worker?.manufacturedProducts} />
          <ListItem static={"Доля бракованных изделий:"} dynamic={worker?.defectedProductsPercent} />
          <ListItem static={"Квалифицирован?:"} dynamic={worker?.isQualified ? "Да" : "Нет"} />
          <ListItem 
            static={"Жалобы:"} 
            dynamic={worker?.complaints?.map((complaint) => (
            <div key={complaint.complaintid}>{complaint.complaintComment}</div>
          ))} />
        </div>
      </div>
    </>
  );
};

export default DetailPage;
