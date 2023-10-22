import { Specialization } from "./specialization";

export interface Qualification {
  id: number;
  qualificationName: string;
  manufacturedProductCount: string;
  defectiveProductsPercentage: string;
  specialization: Specialization;
}

export interface PageDtoQualification {
  content: Qualification[];
  currentPage: number;
  totalCount: number;
}
