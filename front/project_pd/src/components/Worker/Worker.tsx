import React from "react";
import { ShortWorker } from "../../models/worker";
import styles from "./style.module.scss";
import { Link } from "react-router-dom";

interface IProp {
  worker: ShortWorker;
}

const Worker: React.FC<IProp> = ({ worker }) => {
  
  return (
		<Link to={`/worker/${worker.id}`} className={styles.link}> 
    <div className={styles.field}>
      <div className={styles.left}>{worker.id}</div>
      <div className={styles.center}>{worker.fullName} </div>
      <div className={styles.right}>{worker.isQualified ? 'Да' : 'Нет'} </div>
    </div>
		</Link>
  );
};

export default Worker;
