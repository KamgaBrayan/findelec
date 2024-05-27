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
import { ITrajet } from 'app/shared/model/trajet.model';
import { getEntity, updateEntity, createEntity, reset } from './trajet.reducer';

export const TrajetUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const utilisateurs = useAppSelector(state => state.utilisateur.entities);
  const trajetEntity = useAppSelector(state => state.trajet.entity);
  const loading = useAppSelector(state => state.trajet.loading);
  const updating = useAppSelector(state => state.trajet.updating);
  const updateSuccess = useAppSelector(state => state.trajet.updateSuccess);

  const handleClose = () => {
    navigate('/trajet');
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
    values.dateDepart = convertDateTimeToServer(values.dateDepart);
    if (values.nombrePlacesDisponibles !== undefined && typeof values.nombrePlacesDisponibles !== 'number') {
      values.nombrePlacesDisponibles = Number(values.nombrePlacesDisponibles);
    }

    const entity = {
      ...trajetEntity,
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
      ? {
          dateDepart: displayDefaultDateTime(),
        }
      : {
          ...trajetEntity,
          dateDepart: convertDateTimeFromServer(trajetEntity.dateDepart),
          utilisateur: trajetEntity?.utilisateur?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="findelecApp.trajet.home.createOrEditLabel" data-cy="TrajetCreateUpdateHeading">
            <Translate contentKey="findelecApp.trajet.home.createOrEditLabel">Create or edit a Trajet</Translate>
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
                  id="trajet-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('findelecApp.trajet.villeDepart')}
                id="trajet-villeDepart"
                name="villeDepart"
                data-cy="villeDepart"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('findelecApp.trajet.villeArrivee')}
                id="trajet-villeArrivee"
                name="villeArrivee"
                data-cy="villeArrivee"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('findelecApp.trajet.dateDepart')}
                id="trajet-dateDepart"
                name="dateDepart"
                data-cy="dateDepart"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('findelecApp.trajet.nombrePlacesDisponibles')}
                id="trajet-nombrePlacesDisponibles"
                name="nombrePlacesDisponibles"
                data-cy="nombrePlacesDisponibles"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                id="trajet-utilisateur"
                name="utilisateur"
                data-cy="utilisateur"
                label={translate('findelecApp.trajet.utilisateur')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/trajet" replace color="info">
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

export default TrajetUpdate;
