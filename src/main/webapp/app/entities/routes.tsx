import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Objet from './objet';
import Utilisateur from './utilisateur';
import Trajet from './trajet';
import Location from './location';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="objet/*" element={<Objet />} />
        <Route path="utilisateur/*" element={<Utilisateur />} />
        <Route path="trajet/*" element={<Trajet />} />
        <Route path="location/*" element={<Location />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
