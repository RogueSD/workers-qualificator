import React, { useEffect, useState } from "react";
import styles from "./styles.module.scss";
import {
  EditWorker,
  LongWorker,
  getWorker,
  updateWorker,
} from "../../../models/worker";
import { useParams } from "react-router-dom";
import { Specialization, getSpecializationPage } from "../../../models/specialization";

type ModalUpdateProps = {
  isOpen: boolean;
  onClose: () => void;
  editWorker?: EditWorker;
  longWorker?: LongWorker;
};

const ModalUpdate: React.FC<ModalUpdateProps> = (
  { isOpen, onClose, editWorker },
  longWorker
) => {
  const [updatedWorker, setUpdatedWorker] = useState<EditWorker>(editWorker!);
  const [specializations, setSpecializations] = useState<Specialization[]>([]);
  const [fetchWorkers, setFetchWorkers] = useState<LongWorker>(longWorker);
  const { id } = useParams<{ id: any }>();

  const fetchWorker = async () => {
    const response = await getWorker(id);
    setFetchWorkers(response.data);

    setUpdatedWorker({
      id: response.data.id,
      firstName: response.data.firstName,
      lastName: response.data.lastName,
      //@ts-ignore
      specialization: response.data.qualification?.specialization,
      auditComment: response.data.auditComment,
      manufacturedProducts: response.data.manufacturedProducts,
      //@ts-ignore
      defectedProducts: response.data.defectedProductsPercent * response.data.manufacturedProducts,
    });
  };

  useEffect(() => {
    getSpecializationPage(0, 10, "ASC").then((response) => {
      setSpecializations(response.data.content);
    });

    fetchWorker();
  }, []);

  const handleUpdate = async () => {
    try {
      await updateWorker(updatedWorker);
      await updateWorker(updatedWorker);
      onClose();
    } catch (error) {
      console.log("Произошла ошибка при изменении данных");
    }
  };

  if (!isOpen) {
    return null;
  }

  return (
    <div className={styles.opacity}>
      <div className={styles.modal}>
        <div className={styles.heading}>Измените данные о работнике</div>
        <div className={styles.inputs}>
          <div className={styles.label}>Имя</div>
          <input
            type="text"
            className={styles.input}
            placeholder="Имя"
            value={updatedWorker.firstName}
            onChange={(e) =>
              setUpdatedWorker({ ...updatedWorker, firstName: e.target.value })
            }
          />
          <div className={styles.label}>Фамилия</div>
          <input
            type="text"
            className={styles.input}
            placeholder="Фамилия"
            value={updatedWorker.lastName}
            onChange={(e) =>
              setUpdatedWorker({ ...updatedWorker, lastName: e.target.value })
            }
          />
          <div className={styles.label}>Комментарий аудита</div>
          <input
            type="text"
            className={styles.input}
            placeholder="Комментарий аудита"
            value={updatedWorker.auditComment}
            onChange={(e) =>
              setUpdatedWorker({
                ...updatedWorker,
                auditComment: e.target.value,
              })
            }
          />
          <div className={styles.label}>Специализация</div>
          <select
            className={styles.select}
            value={
              updatedWorker.specialization?.id
            }
            onChange={(e) =>
              //@ts-ignore
              setUpdatedWorker((prevWorker) => ({
                ...prevWorker,
                specialization: {
                  id: e.target.value,
                },
                manufacturedProducts: 0,
                defectedProducts: 0
              }))
            }
          >
            {specializations.map((specialization) => (
              <option key={specialization.id} value={specialization.id}>
                {specialization.specializationName}
              </option>
            ))}
          </select>
          <div className={styles.label}>Количество произведенных изделий</div>
          <input
            type="text"
            className={styles.input}
            placeholder="Количество произведенных изделий"
            value={updatedWorker.manufacturedProducts}
            onChange={(e) =>
              setUpdatedWorker({
                ...updatedWorker,
                manufacturedProducts: parseInt(e.target.value),
              })
            }
          />
          <div className={styles.label}>Количество бракованных изделий</div>
          <input
            type="text"
            className={styles.input}
            placeholder="Количество бракованных изделий"
            value={updatedWorker.defectedProducts}
            onChange={(e) =>
              setUpdatedWorker({
                ...updatedWorker,
                defectedProducts: parseInt(e.target.value),
              })
            }
          />
        </div>
        <div className={styles.buttons}>
          <button className={styles.button} onClick={handleUpdate}>
            Изменить
          </button>
          <button className={styles.button} onClick={onClose}>
            Закрыть
          </button>
        </div>
      </div>
    </div>
  );
};

export default ModalUpdate;
