import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import OrderProcessor from './order-processor';
import OrderProcessorDetail from './order-processor-detail';
import OrderProcessorUpdate from './order-processor-update';
import OrderProcessorDeleteDialog from './order-processor-delete-dialog';

const OrderProcessorRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<OrderProcessor />} />
    <Route path="new" element={<OrderProcessorUpdate />} />
    <Route path=":id">
      <Route index element={<OrderProcessorDetail />} />
      <Route path="edit" element={<OrderProcessorUpdate />} />
      <Route path="delete" element={<OrderProcessorDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default OrderProcessorRoutes;
