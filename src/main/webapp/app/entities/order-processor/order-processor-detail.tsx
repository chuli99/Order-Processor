import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './order-processor.reducer';

export const OrderProcessorDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const orderProcessorEntity = useAppSelector(state => state.orderProcessor.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="orderProcessorDetailsHeading">
          <Translate contentKey="orderProcessorApp.orderProcessor.detail.title">OrderProcessor</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{orderProcessorEntity.id}</dd>
          <dt>
            <span id="cliente">
              <Translate contentKey="orderProcessorApp.orderProcessor.cliente">Cliente</Translate>
            </span>
          </dt>
          <dd>{orderProcessorEntity.cliente}</dd>
          <dt>
            <span id="accion">
              <Translate contentKey="orderProcessorApp.orderProcessor.accion">Accion</Translate>
            </span>
          </dt>
          <dd>{orderProcessorEntity.accion}</dd>
          <dt>
            <span id="operacion">
              <Translate contentKey="orderProcessorApp.orderProcessor.operacion">Operacion</Translate>
            </span>
          </dt>
          <dd>{orderProcessorEntity.operacion}</dd>
          <dt>
            <span id="precio">
              <Translate contentKey="orderProcessorApp.orderProcessor.precio">Precio</Translate>
            </span>
          </dt>
          <dd>{orderProcessorEntity.precio}</dd>
          <dt>
            <span id="cantidad">
              <Translate contentKey="orderProcessorApp.orderProcessor.cantidad">Cantidad</Translate>
            </span>
          </dt>
          <dd>{orderProcessorEntity.cantidad}</dd>
          <dt>
            <span id="fechaOperacion">
              <Translate contentKey="orderProcessorApp.orderProcessor.fechaOperacion">Fecha Operacion</Translate>
            </span>
          </dt>
          <dd>
            {orderProcessorEntity.fechaOperacion ? (
              <TextFormat value={orderProcessorEntity.fechaOperacion} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="modo">
              <Translate contentKey="orderProcessorApp.orderProcessor.modo">Modo</Translate>
            </span>
          </dt>
          <dd>{orderProcessorEntity.modo}</dd>
        </dl>
        <Button tag={Link} to="/order-processor" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/order-processor/${orderProcessorEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default OrderProcessorDetail;
