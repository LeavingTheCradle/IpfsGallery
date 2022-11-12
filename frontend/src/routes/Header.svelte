<script>
	import { page } from '$app/stores';
	import logo from '$lib/images/svelte-logo.svg';
	import github from '$lib/images/github.svg';
</script>

<header>
	<nav>
		<ul class="flex border-b">
			<li class="-mb-px mr-1">
				<a class={$page.url.pathname === '/' ? 'navActive' : 'navItem'} href="/">Active</a>
			</li>
			<li class="mr-1">
				<a class={$page.url.pathname === '/gallery' ? 'navActive' : 'navItem'} href="/gallery"
					>Gallery</a
				>
			</li>
			<li class="mr-1">
				<a class={$page.url.pathname === '/' ? 'navActive' : 'navItem'} href="#">Tab</a>
			</li>
			<li class="mr-1">
				<a class={$page.url.pathname === '/' ? 'navActive' : 'navItem'} href="#">Tab</a>
			</li>
		</ul>
	</nav>
</header>

<style>
</style>
