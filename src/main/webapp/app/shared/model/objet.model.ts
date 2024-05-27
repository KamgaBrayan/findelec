import { IUtilisateur } from 'app/shared/model/utilisateur.model';
import { ObjetType } from 'app/shared/model/enumerations/objet-type.model';
import { StatutType } from 'app/shared/model/enumerations/statut-type.model';

export interface IObjet {
  id?: number;
  nom?: string;
  description?: string | null;
  type?: keyof typeof ObjetType;
  statut?: keyof typeof StatutType;
  utilisateur?: IUtilisateur | null;
}

export const defaultValue: Readonly<IObjet> = {};
