import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './trajet.reducer';

export const TrajetDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const trajetEntity = useAppSelector(state => state.trajet.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="trajetDetailsHeading">
          <Translate contentKey="findelecApp.trajet.detail.title">Trajet</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{trajetEntity.id}</dd>
          <dt>
            <span id="villeDepart">
              <Translate contentKey="findelecApp.trajet.villeDepart">Ville Depart</Translate>
            </span>
          </dt>
          <dd>{trajetEntity.villeDepart}</dd>
          <dt>
            <span id="villeArrivee">
              <Translate contentKey="findelecApp.trajet.villeArrivee">Ville Arrivee</Translate>
            </span>
          </dt>
          <dd>{trajetEntity.villeArrivee}</dd>
          <dt>
            <span id="dateDepart">
              <Translate contentKey="findelecApp.trajet.dateDepart">Date Depart</Translate>
            </span>
          </dt>
          <dd>{trajetEntity.dateDepart ? <TextFormat value={trajetEntity.dateDepart} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="nombrePlacesDisponibles">
              <Translate contentKey="findelecApp.trajet.nombrePlacesDisponibles">Nombre Places Disponibles</Translate>
            </span>
          </dt>
          <dd>{trajetEntity.nombrePlacesDisponibles}</dd>
          <dt>
            <Translate contentKey="findelecApp.trajet.utilisateur">Utilisateur</Translate>
          </dt>
          <dd>{trajetEntity.utilisateur ? trajetEntity.utilisateur.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/trajet" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/trajet/${trajetEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TrajetDetail;
