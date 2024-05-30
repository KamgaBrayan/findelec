import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './objet.reducer';

export const ObjetDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const objetEntity = useAppSelector(state => state.objet.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="objetDetailsHeading">
          <Translate contentKey="findelecApp.objet.detail.title">Objet</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{objetEntity.id}</dd>
          <dt>
            <span id="nom">
              <Translate contentKey="findelecApp.objet.nom">Nom</Translate>
            </span>
          </dt>
          <dd>{objetEntity.nom}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="findelecApp.objet.description">Description</Translate>
            </span>
          </dt>
          <dd>{objetEntity.description}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="findelecApp.objet.type">Type</Translate>
            </span>
          </dt>
          <dd>{objetEntity.type}</dd>
          <dt>
            <span id="statut">
              <Translate contentKey="findelecApp.objet.statut">Statut</Translate>
            </span>
          </dt>
          <dd>{objetEntity.statut}</dd>
          <dt>
            <Translate contentKey="findelecApp.objet.utilisateur">Utilisateur</Translate>
          </dt>
          <dd>{objetEntity.utilisateur ? objetEntity.utilisateur.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/objet" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/objet/${objetEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ObjetDetail;
