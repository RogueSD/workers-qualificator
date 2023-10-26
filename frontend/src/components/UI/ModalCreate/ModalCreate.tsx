import React, { useEffect, useState } from "react";
import styles from "./styles.module.scss";
import { EditWorker } from "../../../models/worker";
import { Specialization, getSpecializationPage } from "../../../models/specialization";

type ModalCreateProps = {
  isOpen: boolean;
  onClose: () => void;
  onAdd: (worker: EditWorker) => void;
  editWorker?: EditWorker;
};

const Modal: React.FC<ModalCreateProps> = (
  { isOpen, onClose, onAdd },
  editWorker
) => {
  const [createWorker, setCreateWorker] = useState<EditWorker>(editWorker);
  const [specializations, setSpecializations] = useState<Specialization[]>([]);

  useEffect(() => {
    // Получение списка специализаций
    getSpecializationPage(0, 10, "ASC")
      .then((response) => {
        setSpecializations(response.data.content);
      })
      .catch((error) => {
        console.log("Произошла ошибка при получении специализаций");
      });
  }, []);

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
          <select
            className={styles.select}
            value={createWorker.qualification?.specialization.id}
            onChange={(e) =>
              //@ts-ignore
              setCreateWorker((prevWorker) => ({
                ...prevWorker,
                specialization: {
                  id: e.target.value
                } 
              }))
            }
          >
            {specializations.map((specialization) => (
              <option
                className={styles.option}
                key={specialization.id}
                value={specialization.id}
              >
                {specialization.specializationName}
              </option>
            ))}
          </select>
        </div>
        <div className={styles.buttons}>
          <button className={styles.button} onClick={handleAdd}>
            Добавить
          </button>
          <button className={styles.button} onClick={onClose}>
            Закрыть
          </button>
        </div>
      </div>
    </div>
  );
};

export default Modal;
