import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IOrden } from 'app/shared/model/orden.model';
import { getEntity, updateEntity, createEntity, reset } from './orden.reducer';

export const OrdenUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const ordenEntity = useAppSelector(state => state.orden.entity);
  const loading = useAppSelector(state => state.orden.loading);
  const updating = useAppSelector(state => state.orden.updating);
  const updateSuccess = useAppSelector(state => state.orden.updateSuccess);

  const handleClose = () => {
    navigate('/orden' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
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
    if (values.cliente !== undefined && typeof values.cliente !== 'number') {
      values.cliente = Number(values.cliente);
    }
    if (values.precio !== undefined && typeof values.precio !== 'number') {
      values.precio = Number(values.precio);
    }
    if (values.cantidad !== undefined && typeof values.cantidad !== 'number') {
      values.cantidad = Number(values.cantidad);
    }

    const entity = {
      ...ordenEntity,
      ...values,
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
          ...ordenEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="orderProcessorApp.orden.home.createOrEditLabel" data-cy="OrdenCreateUpdateHeading">
            <Translate contentKey="orderProcessorApp.orden.home.createOrEditLabel">Create or edit a Orden</Translate>
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
                  id="orden-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('orderProcessorApp.orden.cliente')}
                id="orden-cliente"
                name="cliente"
                data-cy="cliente"
                type="text"
              />
              <ValidatedField
                label={translate('orderProcessorApp.orden.accion')}
                id="orden-accion"
                name="accion"
                data-cy="accion"
                type="text"
              />
              <ValidatedField
                label={translate('orderProcessorApp.orden.operacion')}
                id="orden-operacion"
                name="operacion"
                data-cy="operacion"
                type="text"
              />
              <ValidatedField
                label={translate('orderProcessorApp.orden.precio')}
                id="orden-precio"
                name="precio"
                data-cy="precio"
                type="text"
              />
              <ValidatedField
                label={translate('orderProcessorApp.orden.cantidad')}
                id="orden-cantidad"
                name="cantidad"
                data-cy="cantidad"
                type="text"
              />
              <ValidatedField
                label={translate('orderProcessorApp.orden.fechaOperacion')}
                id="orden-fechaOperacion"
                name="fechaOperacion"
                data-cy="fechaOperacion"
                type="date"
              />
              <ValidatedField label={translate('orderProcessorApp.orden.modo')} id="orden-modo" name="modo" data-cy="modo" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/orden" replace color="info">
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

export default OrdenUpdate;
