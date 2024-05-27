import dayjs from 'dayjs';
import { IUtilisateur } from 'app/shared/model/utilisateur.model';

export interface ITrajet {
  id?: number;
  villeDepart?: string;
  villeArrivee?: string;
  dateDepart?: dayjs.Dayjs;
  nombrePlacesDisponibles?: number;
  utilisateur?: IUtilisateur | null;
}

export const defaultValue: Readonly<ITrajet> = {};
