PRAGMA foreign_keys = false;

DROP TABLE IF EXISTS "Department";
CREATE TABLE "Department" (
  "id" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "name" TEXT NOT NULL
);

DROP TABLE IF EXISTS "Employee";
CREATE TABLE "Employee" (
  "id" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "FirstName" TEXT NOT NULL,
  "LastName" TEXT NOT NULL,
  "MidName" TEXT NOT NULL DEFAULT '',
  "BirthDay" TEXT NOT NULL,
  "HiringDay" TEXT NOT NULL,
  "Payment" integer NOT NULL,
  "DepartmentId" INTEGER NOT NULL,
  FOREIGN KEY ("DepartmentId") REFERENCES "Department" ("id") ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE UNIQUE INDEX "name"
ON "Department" (
  "name" ASC
);

CREATE UNIQUE INDEX "person"
ON "Employee" (
  "FirstName" ASC,
  "LastName" ASC,
  "MidName" ASC,
  "BirthDay" ASC
);

PRAGMA foreign_keys = true;