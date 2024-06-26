import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/objet">
        <Translate contentKey="global.menu.entities.objet" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/utilisateur">
        <Translate contentKey="global.menu.entities.utilisateur" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/trajet">
        <Translate contentKey="global.menu.entities.trajet" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/location">
        <Translate contentKey="global.menu.entities.location" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/parcour">
        <Translate contentKey="global.menu.entities.parcour" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
