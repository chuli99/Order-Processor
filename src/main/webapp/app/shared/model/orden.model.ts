export interface IOrden {
  id?: number;
  cliente?: number | null;
  accion?: string | null;
  operacion?: string | null;
  precio?: number | null;
  cantidad?: number | null;
  fechaOperacion?: string | null;
  modo?: string | null;
}

export const defaultValue: Readonly<IOrden> = {};
