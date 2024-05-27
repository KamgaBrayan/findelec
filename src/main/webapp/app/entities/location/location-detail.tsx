import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './location.reducer';

export const LocationDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const locationEntity = useAppSelector(state => state.location.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="locationDetailsHeading">
          <Translate contentKey="findelecApp.location.detail.title">Location</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{locationEntity.id}</dd>
          <dt>
            <span id="adresse">
              <Translate contentKey="findelecApp.location.adresse">Adresse</Translate>
            </span>
          </dt>
          <dd>{locationEntity.adresse}</dd>
          <dt>
            <span id="ville">
              <Translate contentKey="findelecApp.location.ville">Ville</Translate>
            </span>
          </dt>
          <dd>{locationEntity.ville}</dd>
          <dt>
            <span id="pays">
              <Translate contentKey="findelecApp.location.pays">Pays</Translate>
            </span>
          </dt>
          <dd>{locationEntity.pays}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="findelecApp.location.description">Description</Translate>
            </span>
          </dt>
          <dd>{locationEntity.description}</dd>
          <dt>
            <span id="prixParNuit">
              <Translate contentKey="findelecApp.location.prixParNuit">Prix Par Nuit</Translate>
            </span>
          </dt>
          <dd>{locationEntity.prixParNuit}</dd>
          <dt>
            <Translate contentKey="findelecApp.location.utilisateur">Utilisateur</Translate>
          </dt>
          <dd>{locationEntity.utilisateur ? locationEntity.utilisateur.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/location" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/location/${locationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default LocationDetail;
