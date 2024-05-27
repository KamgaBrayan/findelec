import objet from 'app/entities/objet/objet.reducer';
import utilisateur from 'app/entities/utilisateur/utilisateur.reducer';
import trajet from 'app/entities/trajet/trajet.reducer';
import location from 'app/entities/location/location.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  objet,
  utilisateur,
  trajet,
  location,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
