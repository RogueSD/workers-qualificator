import React from "react";
import { ShortWorker } from "../../models/worker";
import styles from "./style.module.scss";
import { Link } from "react-router-dom";

interface IProp {
  worker: ShortWorker;
}

const Worker = ({ worker }: IProp) => {
  return (
		<Link to={`/worker/${worker.id}`} className={styles.link}> 
    <div className={styles.field}>
      <div>{worker.id}</div>
      <div>{worker.fullName} </div>
      <div>{worker.isQualified ? 'Да' : 'Нет'} </div>
    </div>
		</Link>
  );
};

export default Worker;
