import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './utilisateur.reducer';

export const UtilisateurDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const utilisateurEntity = useAppSelector(state => state.utilisateur.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="utilisateurDetailsHeading">
          <Translate contentKey="findelecApp.utilisateur.detail.title">Utilisateur</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{utilisateurEntity.id}</dd>
          <dt>
            <span id="nom">
              <Translate contentKey="findelecApp.utilisateur.nom">Nom</Translate>
            </span>
          </dt>
          <dd>{utilisateurEntity.nom}</dd>
          <dt>
            <span id="prenom">
              <Translate contentKey="findelecApp.utilisateur.prenom">Prenom</Translate>
            </span>
          </dt>
          <dd>{utilisateurEntity.prenom}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="findelecApp.utilisateur.email">Email</Translate>
            </span>
          </dt>
          <dd>{utilisateurEntity.email}</dd>
          <dt>
            <span id="motDePasse">
              <Translate contentKey="findelecApp.utilisateur.motDePasse">Mot De Passe</Translate>
            </span>
          </dt>
          <dd>{utilisateurEntity.motDePasse}</dd>
        </dl>
        <Button tag={Link} to="/utilisateur" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/utilisateur/${utilisateurEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default UtilisateurDetail;
