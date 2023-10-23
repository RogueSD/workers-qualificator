import React, { useEffect, useState } from "react";
import styles from './styles.module.scss'
import { LongWorker, getWorker, updateWorker } from "../../../../models/worker";
import { useParams } from "react-router-dom";

type ModalUpdateProps = {
  isOpen: boolean;
  onClose: () => void;
	longWorker?: LongWorker;
};

const ModalUpdate: React.FC<ModalUpdateProps> = ({ isOpen, onClose }, longWorker) => {
  const [updatedWorker, setUpdatedWorker] = useState<LongWorker>(longWorker);
	const { id } = useParams<{ id: any }>();

  const fetchWorker = async () => {
    const response = await getWorker(id);
    setUpdatedWorker(response.data);
  };

  useEffect(() => {
    fetchWorker();
  }, []);

	const handleUpdate = async () => {
		try {
			await updateWorker(updatedWorker);
			onClose();
		} catch (error) {
			console.log('Произошла ошибка при изменении данных');
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
				<input
					type="text"
					className={styles.input}
					placeholder="Имя"
					value={updatedWorker.firstName}
					onChange={(e) => setUpdatedWorker({ ...updatedWorker, firstName: e.target.value })}
				/>
				<input
					type="text"
					className={styles.input}
					placeholder="Фамилия"
					value={updatedWorker.lastName}
					onChange={(e) => setUpdatedWorker({ ...updatedWorker, lastName: e.target.value })}
				/>
				<input
					type="text"
					className={styles.input}
					placeholder="Комментарий аудита"
					value={updatedWorker.auditComment}
					onChange={(e) => setUpdatedWorker({ ...updatedWorker, auditComment: e.target.value })}
				/>
				<input
					type="text"
					className={styles.input}
					placeholder="Специализация"
					value={updatedWorker.qualification?.specialization.specializationName}
					onChange={(e) => setUpdatedWorker({ ...updatedWorker, specialization: { ...updatedWorker.specialization, specializationName: e.target.value } })}
				/>
				<input
					type="text"
					className={styles.input}
					placeholder="Количество произведенных изделий"
					value={updatedWorker.manufacturedProducts}
					onChange={(e) => setUpdatedWorker({ ...updatedWorker, manufacturedProducts: parseInt(e.target.value) })}
				/>
				<input
					type="text"
					className={styles.input}
					placeholder="Процент бракованных изделий"
					value={updatedWorker.defectedProductsPercent}
					onChange={(e) => setUpdatedWorker({ ...updatedWorker, defectedProductsPercent: parseInt(e.target.value) })}
				/>
        </div>
        <div className={styles.buttons}>
          <button className={styles.button} onClick={handleUpdate}>Изменить</button>
          <button className={styles.button} onClick={onClose}>Закрыть</button>
        </div>
      </div>
    </div>
  );
};

export default ModalUpdate;
