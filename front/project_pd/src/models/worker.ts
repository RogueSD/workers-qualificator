import axios, { AxiosResponse } from 'axios';
import { Qualification } from './qualification';
import { Specialization } from './specialization';
import { Complaint } from './complaint';

export interface ShortWorker {
  id: number;
  fullName: string;
  isQualified: boolean;
}

export interface LongWorker {
  id: number;
  lastName: string;
  firstName: string;
  auditComment?: string;
  qualification?: Qualification;
  specialization: Specialization;
  manufacturedProducts?: number;
  defectedProductsPercent?: number;
  isQualified?: boolean;
  complaints?: Complaint[];
}

export interface PageDtoWorker {
  content: ShortWorker[];
  currentPage: number;
  totalCount: number;
}

// Получение всех работников
export const getWorkersPage = async (page: number = 0, size: number = 10, direction: string = 'ASC'): Promise<AxiosResponse<PageDtoWorker>> => {
  try {
    const response = await axios.get(`/api/worker?page=${page}&size=${size}&direction=${direction}`);
    return response;
  } catch (error: any) {
    throw new Error(error);
  }
};

// Получение конкретного работника по id
export const getWorker = async (id: number): Promise<AxiosResponse<LongWorker>> => {

  try {
    const response = await axios.get(`/api/worker/${id}`);
    return response;
  } catch (error: any) {
    throw new Error(error);
  }
};

// Изменение данных о работнике
export const updateWorker = async (worker: LongWorker): Promise<void> => {
  try {
    await axios.put(`api/worker`, worker);
  } catch (error: any) {
    throw new Error(error);
  }
};

// Добавление нового работника
export const createWorker = async (worker: LongWorker): Promise<void> => {
  try {
    await axios.post('/api/worker', worker);
  } catch (error: any) {
    throw new Error(error);
  }
};