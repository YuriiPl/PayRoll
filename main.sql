PRAGMA foreign_keys = false;

CREATE TABLE IF NOT EXISTS "Department" (
  "id" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "name" TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS "Employee" (
  "id" INTEGER NOT NULL DEFAULT '' PRIMARY KEY AUTOINCREMENT,
  "FirstName" TEXT NOT NULL,
  "LastName" TEXT NOT NULL,
  "MidName" TEXT NOT NULL DEFAULT '',
  "BirthDay" TEXT NOT NULL,
  "HiringDay" TEXT NOT NULL,
  "Payment" integer NOT NULL,
  "DepartmentId" INTEGER NOT NULL,
  "PositionType" integer NOT NULL,
  "PositionName" TEXT NOT NULL,
  "ManagerId" INTEGER NOT NULL DEFAULT 0,
  "Description" TEXT NOT NULL DEFAULT '',
  FOREIGN KEY ("DepartmentId") REFERENCES "Department" ("id") ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT "PositionType" CHECK (PositionType IN (1,2,4))
);

CREATE UNIQUE INDEX IF NOT EXISTS "name"
ON "Department" (
  "name" ASC
);

CREATE UNIQUE INDEX IF NOT EXISTS "person"
ON "Employee" (
  "FirstName" ASC,
  "LastName" ASC,
  "MidName" ASC,
  "BirthDay" ASC
);

PRAGMA foreign_keys = true;
