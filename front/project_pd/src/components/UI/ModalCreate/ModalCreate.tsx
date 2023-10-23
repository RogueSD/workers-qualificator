import React, { useState } from "react";
import styles from "./styles.module.scss";
import { LongWorker } from "../../../models/worker";

type ModalCreateProps = {
  isOpen: boolean;
  onClose: () => void;
  onAdd: (worker: LongWorker) => void;
	long?: LongWorker
};

const Modal: React.FC<ModalCreateProps> = ({ isOpen, onClose, onAdd }, long) => {
  const [createWorker, setCreateWorker] = useState<LongWorker>(long);

	const handleAdd = () => {
    onAdd(createWorker);
  };
	
  if (!isOpen) {
    return null;
  }

  return (
    <div className={styles.opacity}>
      <div className={styles.modal}>
        <div className={styles.heading}>Добавьте пользователя</div>
        <div className={styles.inputs}>
          <input 
						type="text" 
						className={styles.input} 
						placeholder="Имя" 
						value={createWorker?.firstName}
            onChange={(e) =>
              setCreateWorker((prevWorker) => ({
                ...prevWorker,
                firstName: e.target.value,
              }))
            }
					/>
          <input 
						type="text" 
						className={styles.input} 
						placeholder="Фамилия" 
						value={createWorker?.lastName}
            onChange={(e) =>
              setCreateWorker((prevWorker) => ({
                ...prevWorker,
                lastName: e.target.value,
              }))
            }
					/>
          <input 
						type="text" 
						className={styles.input} 
						placeholder="Специализация" 
						value={createWorker?.qualification?.specialization.specializationName}
            onChange={(e) =>
              setCreateWorker((prevWorker) => ({
                ...prevWorker,
                specialization: {
                  specializationName: e.target.value,
                },
              }))
            }
					/>
        </div>
				<div className={styles.buttons}>
					<button className={styles.button} onClick={handleAdd}>Добавить</button>
					<button className={styles.button} onClick={onClose}>Закрыть</button>
				</div>
      </div>
    </div>
  );
};

export default Modal;
