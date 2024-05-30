import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IUtilisateur } from 'app/shared/model/utilisateur.model';
import { getEntities as getUtilisateurs } from 'app/entities/utilisateur/utilisateur.reducer';
import { IObjet } from 'app/shared/model/objet.model';
import { ObjetType } from 'app/shared/model/enumerations/objet-type.model';
import { StatutType } from 'app/shared/model/enumerations/statut-type.model';
import { getEntity, updateEntity, createEntity, reset } from './objet.reducer';

export const ObjetUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const utilisateurs = useAppSelector(state => state.utilisateur.entities);
  const objetEntity = useAppSelector(state => state.objet.entity);
  const loading = useAppSelector(state => state.objet.loading);
  const updating = useAppSelector(state => state.objet.updating);
  const updateSuccess = useAppSelector(state => state.objet.updateSuccess);
  const objetTypeValues = Object.keys(ObjetType);
  const statutTypeValues = Object.keys(StatutType);

  const handleClose = () => {
    navigate('/objet');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getUtilisateurs({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }

    const entity = {
      ...objetEntity,
      ...values,
      utilisateur: utilisateurs.find(it => it.id.toString() === values.utilisateur?.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          type: 'ELECTRONIQUE',
          statut: 'DISPONIBLE',
          ...objetEntity,
          utilisateur: objetEntity?.utilisateur?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="findelecApp.objet.home.createOrEditLabel" data-cy="ObjetCreateUpdateHeading">
            <Translate contentKey="findelecApp.objet.home.createOrEditLabel">Create or edit a Objet</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="objet-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('findelecApp.objet.nom')}
                id="objet-nom"
                name="nom"
                data-cy="nom"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('findelecApp.objet.description')}
                id="objet-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField label={translate('findelecApp.objet.type')} id="objet-type" name="type" data-cy="type" type="select">
                {objetTypeValues.map(objetType => (
                  <option value={objetType} key={objetType}>
                    {translate('findelecApp.ObjetType.' + objetType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField label={translate('findelecApp.objet.statut')} id="objet-statut" name="statut" data-cy="statut" type="select">
                {statutTypeValues.map(statutType => (
                  <option value={statutType} key={statutType}>
                    {translate('findelecApp.StatutType.' + statutType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                id="objet-utilisateur"
                name="utilisateur"
                data-cy="utilisateur"
                label={translate('findelecApp.objet.utilisateur')}
                type="select"
              >
                <option value="" key="0" />
                {utilisateurs
                  ? utilisateurs.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/objet" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default ObjetUpdate;
