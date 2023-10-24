import React from "react";
import styles from "./styles.module.scss";

interface IProp {
  static: string;
  dynamic: any;
}

export const ListItem: React.FC<IProp> = (props) => {
  return ( 
			<div className={styles.list}>
				<div className={styles.static}>
					{props.static}
				</div>
				<div className={styles.dynamic}>
					{props.dynamic}
				</div>
			</div>
	);
};

export default ListItem;
