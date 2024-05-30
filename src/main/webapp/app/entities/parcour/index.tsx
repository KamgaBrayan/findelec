import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Parcour from './parcour';
import ParcourDetail from './parcour-detail';
import ParcourUpdate from './parcour-update';
import ParcourDeleteDialog from './parcour-delete-dialog';

const ParcourRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Parcour />} />
    <Route path="new" element={<ParcourUpdate />} />
    <Route path=":id">
      <Route index element={<ParcourDetail />} />
      <Route path="edit" element={<ParcourUpdate />} />
      <Route path="delete" element={<ParcourDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ParcourRoutes;
