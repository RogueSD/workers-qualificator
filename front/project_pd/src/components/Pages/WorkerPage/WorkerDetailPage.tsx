import React, { useEffect, useState } from "react";
import styles from "./style.module.scss";
import { Worker, getWorker } from "../../../models/worker";
import { useParams } from "react-router-dom";

const DetailPage = () => {
  const [worker, setWorker] = useState<Worker>();
  const { id } = useParams<{ id: any }>();

  useEffect(() => {
    const fetchWorker = async () => {
      const response = await getWorker(id);
      setWorker(response.data);
    };

    fetchWorker();
  }, []);

  return (
    <>
      <div className={styles.field}>
        <div>Идентификатор: {worker?.id}</div>
        <div>Фамилия: {worker?.lastName}</div>
        <div>Имя: {worker?.firstName}</div>
        <div>Комментарий аудита: {worker?.auditComment}</div>
        <div>Квалификация: {worker?.qualification.qualificationName}</div>
				<div>Количество произведенной продукции: {worker?.qualification.manufacturedProductCount}</div>
				<div>Процент бракованной продукции: {worker?.qualification.defectiveProductsPercentage}</div>
        <div>Специализация: {worker?.qualification.specialization.specializationName}</div>
        <div>Количество произведенных изделий: {worker?.manufacturedProducts}</div>
        <div>Процент бракованных изделий: {worker?.defectedProductsPercent}</div>
        <div>Квалифицирован: {worker?.isQualified ? "Да" : "Нет"}</div>
        <div>Жалобы:</div>
        {worker?.complaints.map((complaint) => (
          <div key={complaint.complaintid}>{complaint.complaintComment}</div>
        ))}
      </div>
    </>
  );
};

export default DetailPage;
