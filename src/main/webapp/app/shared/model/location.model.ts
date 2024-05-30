import { IUtilisateur } from 'app/shared/model/utilisateur.model';

export interface ILocation {
  id?: number;
  adresse?: string;
  ville?: string;
  pays?: string;
  description?: string | null;
  prixParNuit?: number;
  utilisateur?: IUtilisateur | null;
}

export const defaultValue: Readonly<ILocation> = {};
