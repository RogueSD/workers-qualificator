import axios, { AxiosResponse } from 'axios';
import { Qualification } from './qualification';
import { Specialization } from './specialization';
import { Complaint } from './complaint';

export interface ShortWorker {
  id: number;
  fullName: string;
  isQualified: boolean;
}

export interface Worker {
  id: number;
  lastName: string;
  firstName: string;
  auditComment: string;
  qualification: Qualification;
  specialization: Specialization;
  manufacturedProducts: number;
  defectedProductsPercent: number;
  isQualified: boolean;
  complaints: Complaint[];
}

export interface PageDtoWorker {
  content: ShortWorker[];
  currentPage: number;
  totalCount: number;
}

export const getWorkersPage = async (page: number = 0, size: number = 10, direction: string = 'ASC'): Promise<AxiosResponse<PageDtoWorker>> => {
  try {
    const response = await axios.get(`/api/worker?page=${page}&size=${size}&direction=${direction}`);
    return response;
  } catch (error: any) {
    throw new Error(error);
  }
};

export const getWorker = async (id: number): Promise<AxiosResponse<Worker>> => {
  try {
    const response = await axios.get(`/api/worker/${id}`);
    return response;
  } catch (error: any) {
    throw new Error(error);
  }
};

export const updateWorker = async (worker: Worker): Promise<void> => {
  try {
    await axios.put('/worker', worker);
  } catch (error: any) {
    throw new Error(error);
  }
};

export const createWorker = async (worker: Omit<Worker, 'id'>): Promise<void> => {
  try {
    await axios.post('/worker', worker);
  } catch (error: any) {
    throw new Error(error);
  }
};