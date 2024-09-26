import { createMenuItems } from '@vaadin/hilla-file-router/runtime.js';
import { NavLink, Outlet } from 'react-router-dom';
import { Suspense } from 'react';

export default function MainLayout() {

    return (
        <div className="p-m h-full flex flex-col box-border">
            <header className="flex gap-m pb-m">
                <h1 className="text-l m-0">
                    My Hilla App
                </h1>
                {createMenuItems().map(({ to, title }) => (
                    <NavLink to={to} key={to}>
                        {title}
                    </NavLink>
                ))}
            </header>
            <Suspense>
                <Outlet />
            </Suspense>
        </div>
    );
}
