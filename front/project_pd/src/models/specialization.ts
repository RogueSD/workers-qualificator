export interface Specialization {
  id?: number;
  specializationName: string;
}

export interface PageDtoSpecialization {
  content: Specialization[];
  currentPage: number;
  totalCount: number;
}