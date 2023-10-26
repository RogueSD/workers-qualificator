import axios, { AxiosResponse } from "axios";

export interface Specialization {
  id: number;
  specializationName: string;
}

export interface PageDtoSpecialization {
  content: Specialization[];
  currentPage: number;
  totalCount: number;
}

// Получение списка специализаций
export const getSpecializationPage = async (page: number = 0, size: number = 10, direction: string = 'ASC'): Promise<AxiosResponse<PageDtoSpecialization>> => {
  try {
    const response = await axios.get(`/api/specialization?page=${page}&size=${size}&direction=${direction}`);
    return response;
  } catch (error: any) {
    throw new Error(error);
  }
};