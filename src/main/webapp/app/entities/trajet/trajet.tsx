import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './trajet.reducer';

export const Trajet = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const trajetList = useAppSelector(state => state.trajet.entities);
  const loading = useAppSelector(state => state.trajet.loading);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        sort: `${sortState.sort},${sortState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?sort=${sortState.sort},${sortState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [sortState.order, sortState.sort]);

  const sort = p => () => {
    setSortState({
      ...sortState,
      order: sortState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = sortState.sort;
    const order = sortState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    } else {
      return order === ASC ? faSortUp : faSortDown;
    }
  };

  return (
    <div>
      <h2 id="trajet-heading" data-cy="TrajetHeading">
        <Translate contentKey="findelecApp.trajet.home.title">Trajets</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="findelecApp.trajet.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/trajet/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="findelecApp.trajet.home.createLabel">Create new Trajet</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {trajetList && trajetList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="findelecApp.trajet.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('villeDepart')}>
                  <Translate contentKey="findelecApp.trajet.villeDepart">Ville Depart</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('villeDepart')} />
                </th>
                <th className="hand" onClick={sort('villeArrivee')}>
                  <Translate contentKey="findelecApp.trajet.villeArrivee">Ville Arrivee</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('villeArrivee')} />
                </th>
                <th className="hand" onClick={sort('dateDepart')}>
                  <Translate contentKey="findelecApp.trajet.dateDepart">Date Depart</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('dateDepart')} />
                </th>
                <th className="hand" onClick={sort('nombrePlacesDisponibles')}>
                  <Translate contentKey="findelecApp.trajet.nombrePlacesDisponibles">Nombre Places Disponibles</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('nombrePlacesDisponibles')} />
                </th>
                <th>
                  <Translate contentKey="findelecApp.trajet.utilisateur">Utilisateur</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {trajetList.map((trajet, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/trajet/${trajet.id}`} color="link" size="sm">
                      {trajet.id}
                    </Button>
                  </td>
                  <td>{trajet.villeDepart}</td>
                  <td>{trajet.villeArrivee}</td>
                  <td>{trajet.dateDepart ? <TextFormat type="date" value={trajet.dateDepart} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{trajet.nombrePlacesDisponibles}</td>
                  <td>{trajet.utilisateur ? <Link to={`/utilisateur/${trajet.utilisateur.id}`}>{trajet.utilisateur.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/trajet/${trajet.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/trajet/${trajet.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/trajet/${trajet.id}/delete`)}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="findelecApp.trajet.home.notFound">No Trajets found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Trajet;
