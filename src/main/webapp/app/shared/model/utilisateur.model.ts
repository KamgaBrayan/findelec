export interface IUtilisateur {
  id?: number;
  nom?: string;
  prenom?: string;
  email?: string;
  motDePasse?: string;
}

export const defaultValue: Readonly<IUtilisateur> = {};
